export class Room{
    ownerEmail: string
    roomID: number
    participantsIDs: string []
    description: string
    value: number
    status: string

    constructor () {
        this.participantsIDs = [];
        this.description = "";
        this.value = 0;
        this.status = "active";
     }
}

