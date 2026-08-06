export interface Transaction {

  id: number;

  userId?: number;

  beneficiaryName: string;

  amount: number;

  status: string;

  transactionDate: string;

  balance?: number;

  remarks?: string;

  transactionMode?: string;

  transactionType?: string;

  // New fields

  senderName?: string;

  receiverName?: string;

  fromAccount?: string;

  toAccount?: string;

}