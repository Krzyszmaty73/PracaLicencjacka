import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../user';
import { Observable } from 'rxjs';



@Injectable({
    providedIn: 'root'
})

export class UserService {

    private apiUrl = 'http://localhost:8080';

    users: User[] = []

    constructor(private http: HttpClient) { }

    public getUsers(users: unknown): any {
        const userObservable = new Observable(observer => {
            observer.next(users);
        });
        return userObservable;
    }

    public addNewUser(newUser: User) {
        console.log(newUser);
        console.log(this.getUsers.length)
        this.users.push(newUser);
    }

    getAll() {
        return this.http.get<User[]>(`${this.apiUrl}/users`);
    }

    register(user: User) {
        console.log("Rejestruje" + user)
        return this.http.post<any>(`${this.apiUrl}/users/register/`, user);
    }


}