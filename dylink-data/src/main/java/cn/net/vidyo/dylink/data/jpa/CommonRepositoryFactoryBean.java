package cn.net.vidyo.dylink.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class CommonRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, ID> {

    public CommonRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        // TODO Auto-generated method stub
        return new CommonRepositoryFactory(entityManager);
    }

    private static class CommonRepositoryFactory<S, ID extends Serializable> extends JpaRepositoryFactory {

        public CommonRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }


        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation<?, Serializable> entityInformation = this.getEntityInformation(information.getDomainType());
            return new CommonJpaRepositoryImpl<>(entityInformation,entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            // TODO Auto-generated method stub
            return CommonJpaRepository.class;
        }

    }
}
