import { Routes } from '@angular/router';

export const routes: Routes = [

  {
    path: '',
    loadComponent: () =>
      import('./components/login/login')
        .then(m => m.Login)
  },
{
  path: 'login',
  loadComponent: () =>
    import('./components/login/login')
      .then(m => m.Login)
},
  {
    path: 'signup',
    loadComponent: () =>
      import('./auth/signup/signup')
        .then(m => m.Signup)
  },

  {
    path: 'forgot-password',
    loadComponent: () =>
      import('./auth/forgot-password/forgot-password')
        .then(m => m.ForgotPassword)
  },

  {
    path: 'dashboard',
    loadComponent: () =>
      import('./components/dashboard/dashboard')
        .then(m => m.Dashboard)
  },

  {
    path: 'add-beneficiary',
    loadComponent: () =>
      import('./components/beneficiaries/add-beneficiary/add-beneficiary')
        .then(m => m.AddBeneficiary)
  },

  {
    path: 'beneficiary-list',
    loadComponent: () =>
      import('./components/beneficiaries/beneficiary-list/beneficiary-list')
        .then(m => m.BeneficiaryList)
  },

  {
  path: 'transactions',
  loadComponent: () =>
    import('./components/transactions/transaction-history/transaction-history')
      .then(m => m.TransactionHistory)
  },

  {
    path: 'transfer-money',
    loadComponent: () =>
      import('./components/transfer/transfer-money/transfer-money')
        .then(m => m.TransferMoney)
  },

  {
  path: 'profile',
  loadComponent: () =>
    import('./components/profile/profile-details/profile-details')
      .then(m => m.ProfileDetails)
  },

  {
  path: 'statement',
  loadComponent: () =>
    import('./components/statements/account-statement/account-statement')
      .then(m => m.AccountStatement)
  },

  {
    path: 'otp-verification',
    loadComponent: () =>
      import('./components/transfer/otp-verification/otp-verification')
        .then(m => m.OtpVerification)
  },

  {
  path:'set-pin',
  loadComponent:()=>
  import('./components/transaction-pin/set-transaction-pin/set-transaction-pin')
  .then(m=>m.SetTransactionPin)
},

{
  path:'change-pin',
  loadComponent:()=>
  import('./components/transaction-pin/change-transaction-pin/change-transaction-pin')
  .then(m=>m.ChangeTransactionPin)
},

  {
  path: '**',
  loadComponent: () =>
    import('./components/not-found/not-found')
      .then(m => m.NotFound)
  }

];