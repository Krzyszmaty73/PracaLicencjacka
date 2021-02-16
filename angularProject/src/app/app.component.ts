import { Component } from '@angular/core'
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service'
import { User } from './user'

@Component({ selector: 'app-root', templateUrl: 'app.component.html' })
export class AppComponent {
    currentUser: User;
    title = 'Split&Go';
    user = ''
    

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }

    home(){
        this.router.navigate(['/home']);
    }
}