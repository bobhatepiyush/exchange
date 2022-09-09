import { IProductContext } from '@/shared/model/product-context.model';
import { IClient } from '@/shared/model/client.model';

export interface IChannel {
  id?: number;
  name?: string | null;
  homeLink?: string | null;
  secretKey?: string | null;
  isActive?: boolean | null;
  productContexts?: IProductContext[] | null;
  client?: IClient | null;
}

export class Channel implements IChannel {
  constructor(
    public id?: number,
    public name?: string | null,
    public homeLink?: string | null,
    public secretKey?: string | null,
    public isActive?: boolean | null,
    public productContexts?: IProductContext[] | null,
    public client?: IClient | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}
