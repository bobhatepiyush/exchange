import { IChannel } from '@/shared/model/channel.model';
import { ILiquidationPartner } from '@/shared/model/liquidation-partner.model';
import { IDeliveryPartner } from '@/shared/model/delivery-partner.model';

export interface IClient {
  id?: number;
  name?: string | null;
  secretKey?: string | null;
  isActive?: boolean | null;
  channels?: IChannel[] | null;
  liquidationPartners?: ILiquidationPartner[] | null;
  deliveryPartners?: IDeliveryPartner[] | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public name?: string | null,
    public secretKey?: string | null,
    public isActive?: boolean | null,
    public channels?: IChannel[] | null,
    public liquidationPartners?: ILiquidationPartner[] | null,
    public deliveryPartners?: IDeliveryPartner[] | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}
