export interface Transaction {
  id: number;
  beneficiaryName: string;
  amount: number;
  status: string;
  transactionDate: string;

  balance?: number;
  remarks?: string;
  transactionMode?: string;
  transactionType?: string;
  userId?: number;
}