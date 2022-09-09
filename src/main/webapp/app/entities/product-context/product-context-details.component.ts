import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProductContext } from '@/shared/model/product-context.model';
import ProductContextService from './product-context.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ProductContextDetails extends Vue {
  @Inject('productContextService') private productContextService: () => ProductContextService;
  @Inject('alertService') private alertService: () => AlertService;

  public productContext: IProductContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productContextId) {
        vm.retrieveProductContext(to.params.productContextId);
      }
    });
  }

  public retrieveProductContext(productContextId) {
    this.productContextService()
      .find(productContextId)
      .then(res => {
        this.productContext = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
