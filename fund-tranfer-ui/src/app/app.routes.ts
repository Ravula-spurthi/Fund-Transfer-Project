import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
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
  }
];