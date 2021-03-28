import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { appRoutingModule } from './app-routing.module';
import { NgxChartsModule }from '@swimlane/ngx-charts';
import { NgxCaptchaModule } from 'ngx-captcha';
import { AppComponent } from './app.component';
import { RoomComponent } from './room/room.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './home/home.component';
import { ReceiptComponent } from './receipt/receipt.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AlertComponent } from './alert/alert.component';
import { JwtInterceptor } from './services/jwt.interceptor';
import { ErrorInterceptor } from './services/error.interceptor';
import { SummaryComponent } from './summary/summary.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    RoomComponent,
    HomeComponent,
    ReceiptComponent,
    LoginComponent,
    RegisterComponent,
    AlertComponent,
    SummaryComponent,


  ],
  imports: [
    BrowserModule,
    appRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxChartsModule,
    NgxCaptchaModule,
    BrowserAnimationsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }