import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ProductContextService from '@/entities/product-context/product-context.service';
import { IProductContext } from '@/shared/model/product-context.model';

import ClientService from '@/entities/client/client.service';
import { IClient } from '@/shared/model/client.model';

import { IChannel, Channel } from '@/shared/model/channel.model';
import ChannelService from './channel.service';

const validations: any = {
  channel: {
    name: {},
    homeLink: {},
    secretKey: {},
    isActive: {},
  },
};

@Component({
  validations,
})
export default class ChannelUpdate extends Vue {
  @Inject('channelService') private channelService: () => ChannelService;
  @Inject('alertService') private alertService: () => AlertService;

  public channel: IChannel = new Channel();

  @Inject('productContextService') private productContextService: () => ProductContextService;

  public productContexts: IProductContext[] = [];

  @Inject('clientService') private clientService: () => ClientService;

  public clients: IClient[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.channelId) {
        vm.retrieveChannel(to.params.channelId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.channel.id) {
      this.channelService()
        .update(this.channel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('exchangeApp.channel.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.channelService()
        .create(this.channel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('exchangeApp.channel.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveChannel(channelId): void {
    this.channelService()
      .find(channelId)
      .then(res => {
        this.channel = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productContextService()
      .retrieve()
      .then(res => {
        this.productContexts = res.data;
      });
    this.clientService()
      .retrieve()
      .then(res => {
        this.clients = res.data;
      });
  }
}
