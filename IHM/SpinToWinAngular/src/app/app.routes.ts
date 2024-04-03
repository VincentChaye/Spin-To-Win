import { Routes } from '@angular/router'; 


// Routage par défaut vers le module public et chargement de ce module à ce moment-là avec le loadChildren
export const routes: Routes = [
    {
        path: '', 
        loadChildren: () => import('./public/public.module')
            .then(m => m.PublicModule)
    }
];
