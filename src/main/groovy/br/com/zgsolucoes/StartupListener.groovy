package br.com.zgsolucoes

import br.com.zgsolucoes.aop.domain.Tree
import br.com.zgsolucoes.aop.domain.Car
import br.com.zgsolucoes.aop.repository.TreeRepository
import br.com.zgsolucoes.aop.repository.CarRepository
import br.com.zgsolucoes.aop.tenant.Tenants
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton

import javax.inject.Inject

@Slf4j
@CompileStatic
@Singleton
class StartupListener {

	@Inject
	CarRepository carroRepository

	@Inject
	TreeRepository arvoreRepository

	@EventListener
	void onStartup(ServiceReadyEvent event) {
		Tenants.withId(100L) {
			arvoreRepository.save(new Tree(species: 'A1', family: 'silva'))
			carroRepository.save(new Car(model: 'M1', brand: 'BMW'))

			arvoreRepository.findAll().each {
				log.info(it.toString())
			}

			carroRepository.findAll().each {
				log.info(it.toString())
			}
		}
	}

}
