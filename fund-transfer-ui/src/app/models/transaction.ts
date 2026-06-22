export interface Transaction {
  transactionId: number;
  fromAccount: string;
  toAccount: string;
  amount: number;
  date: string;
  status: string;
}