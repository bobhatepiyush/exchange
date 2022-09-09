import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ChannelService from '@/entities/channel/channel.service';
import { IChannel } from '@/shared/model/channel.model';

import LiquidationPartnerService from '@/entities/liquidation-partner/liquidation-partner.service';
import { ILiquidationPartner } from '@/shared/model/liquidation-partner.model';

import DeliveryPartnerService from '@/entities/delivery-partner/delivery-partner.service';
import { IDeliveryPartner } from '@/shared/model/delivery-partner.model';

import { IClient, Client } from '@/shared/model/client.model';
import ClientService from './client.service';

const validations: any = {
  client: {
    name: {},
    secretKey: {},
    isActive: {},
  },
};

@Component({
  validations,
})
export default class ClientUpdate extends Vue {
  @Inject('clientService') private clientService: () => ClientService;
  @Inject('alertService') private alertService: () => AlertService;

  public client: IClient = new Client();

  @Inject('channelService') private channelService: () => ChannelService;

  public channels: IChannel[] = [];

  @Inject('liquidationPartnerService') private liquidationPartnerService: () => LiquidationPartnerService;

  public liquidationPartners: ILiquidationPartner[] = [];

  @Inject('deliveryPartnerService') private deliveryPartnerService: () => DeliveryPartnerService;

  public deliveryPartners: IDeliveryPartner[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientId) {
        vm.retrieveClient(to.params.clientId);
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
    if (this.client.id) {
      this.clientService()
        .update(this.client)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('exchangeApp.client.updated', { param: param.id });
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
      this.clientService()
        .create(this.client)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('exchangeApp.client.created', { param: param.id });
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

  public retrieveClient(clientId): void {
    this.clientService()
      .find(clientId)
      .then(res => {
        this.client = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.channelService()
      .retrieve()
      .then(res => {
        this.channels = res.data;
      });
    this.liquidationPartnerService()
      .retrieve()
      .then(res => {
        this.liquidationPartners = res.data;
      });
    this.deliveryPartnerService()
      .retrieve()
      .then(res => {
        this.deliveryPartners = res.data;
      });
  }
}
