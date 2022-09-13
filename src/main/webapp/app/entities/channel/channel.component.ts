import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IChannel } from '@/shared/model/channel.model';

import ChannelService from './channel.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Channel extends Vue {
  @Inject('channelService') private channelService: () => ChannelService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public channels: IChannel[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllChannels();
  }

  public clear(): void {
    this.retrieveAllChannels();
  }

  public retrieveAllChannels(): void {
    this.isFetching = true;
    this.channelService()
      .retrieve()
      .then(
        res => {
          this.channels = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IChannel): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeChannel(): void {
    this.channelService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('exchangeApp.channel.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllChannels();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
