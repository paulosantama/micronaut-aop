package br.com.zgsolucoes.aop.dominio

import com.vladmihalcea.hibernate.type.array.StringArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import org.hibernate.type.TextType

import javax.persistence.*
import java.time.LocalDateTime

@CompileStatic
@MappedSuperclass
@EqualsAndHashCode(excludes = ['version'])
@ToString(includePackage = false, includeNames = true, excludes = ['version'])
@TypeDefs([
		@TypeDef(name = 'jsonb', typeClass = JsonBinaryType),
		@TypeDef(name = 'string-list', typeClass = JsonStringType), // List<String>
		@TypeDef(name = 'string-array', typeClass = StringArrayType), // string[]
		@TypeDef(name = 'string', defaultForType = String, typeClass = TextType),
])
abstract class Entidade {

	@Id
	@GeneratedValue
	UUID id

	@Version
	@Column(nullable = false)
	Integer version

	@DateCreated
	@Column(updatable = false)
	LocalDateTime criadoEm

	@DateUpdated
	LocalDateTime atualizadoEm

	@Column(updatable = false)
	String criadoPor

	String atualizadoPor

}
