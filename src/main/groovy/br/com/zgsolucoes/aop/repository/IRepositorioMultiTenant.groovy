package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.annotations.WithTenant
import br.com.zgsolucoes.aop.annotations.WithUserDetails

@WithTenant
@WithUserDetails
interface IRepositorioMultiTenant<T> {

}
