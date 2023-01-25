package br.com.zgsolucoes

import br.com.zgsolucoes.aop.domain.Tree
import br.com.zgsolucoes.aop.domain.Car
import br.com.zgsolucoes.aop.repository.ArvoreRepository
import br.com.zgsolucoes.aop.repository.CarroRepository
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
	CarroRepository carroRepository

	@Inject
	ArvoreRepository arvoreRepository

	@EventListener
	void onStartup(ServiceReadyEvent event) {
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
