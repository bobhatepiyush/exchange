import { IQustion } from '@/shared/model/qustion.model';
import { IAnswerContext } from '@/shared/model/answer-context.model';
import { IQcQuestion } from '@/shared/model/qc-question.model';
import { IChannel } from '@/shared/model/channel.model';

export interface IProductContext {
  id?: number;
  l1?: string | null;
  l2?: string | null;
  l3?: string | null;
  contextId?: string | null;
  isActive?: boolean | null;
  qustions?: IQustion[] | null;
  answerContexts?: IAnswerContext[] | null;
  qcQuestions?: IQcQuestion[] | null;
  client?: IChannel | null;
}

export class ProductContext implements IProductContext {
  constructor(
    public id?: number,
    public l1?: string | null,
    public l2?: string | null,
    public l3?: string | null,
    public contextId?: string | null,
    public isActive?: boolean | null,
    public qustions?: IQustion[] | null,
    public answerContexts?: IAnswerContext[] | null,
    public qcQuestions?: IQcQuestion[] | null,
    public client?: IChannel | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}
