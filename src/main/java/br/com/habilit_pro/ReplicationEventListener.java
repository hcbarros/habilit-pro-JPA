package br.com.habilit_pro;

import br.com.habilit_pro.models.Trilha;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;

import java.time.LocalDate;

public class ReplicationEventListener implements PostUpdateEventListener,
                                                PostInsertEventListener {

    public static final ReplicationEventListener INSTANCE = new ReplicationEventListener();

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        final Object entity = event.getEntity();
        if(entity instanceof Trilha) {
            Trilha trilha = (Trilha) entity;
            execute(event.getSession(), trilha);
        }
    }

    @Override
    public void onPostInsert(PostInsertEvent event) throws HibernateException {
        final Object entity = event.getEntity();
        if(entity instanceof Trilha) {
            Trilha trilha = (Trilha) entity;
            execute(event.getSession(), trilha);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) { return false; }

    private void execute(EventSource eventSource, Trilha trilha) {
        Long query = eventSource.createQuery(
                "select count(t) from Trilha t where lower(t.ocupacao) = :ocupacao and lower(t.empresa.nome) = :nomeEmpresa",
                Long.class)
                .setParameter("ocupacao", trilha.getOcupacao().toLowerCase())
                .setParameter("nomeEmpresa", trilha.getEmpresa().getNome().toLowerCase())
                .getSingleResult();

        eventSource.createNativeQuery(
                        "UPDATE Trilha set nome = :ocupacao || :nomeEmpresa || :quantia || :ano WHERE nome = NULL")
                .setParameter("ocupacao", trilha.getOcupacao())
                .setParameter("nomeEmpresa", trilha.getEmpresa().getNome())
                .setParameter("quantia", query == null ? 0 : query)
                .setParameter("ano", LocalDate.now().getYear())
                .setFlushMode(FlushMode.MANUAL)
                .executeUpdate();
    }

}

