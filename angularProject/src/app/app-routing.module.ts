import { Routes, RouterModule } from '@angular/router'
import { RoomComponent } from './room/room.component'
import { HomeComponent}  from'./home/home.component'
import { ReceiptComponent } from './receipt/receipt.component'
import { LoginComponent } from './login/login.component'
import { RegisterComponent } from './register/register.component'
import { AuthGuard } from './services/auth.guard'
import { SummaryComponent } from './summary/summary.component'



const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard] },
  {path:'home/create/:roomID/receipt/:receiptID', component:ReceiptComponent},
  {path:'home/create/:roomID', component:RoomComponent, canActivate: [AuthGuard]},
  {path:'home', component:HomeComponent, canActivate: [AuthGuard]},
  {path:'login', component:LoginComponent},
  {path:'register', component:RegisterComponent},
  {path:'room/summary/:roomID', component:SummaryComponent}
];

export const appRoutingModule = RouterModule.forRoot(routes);

