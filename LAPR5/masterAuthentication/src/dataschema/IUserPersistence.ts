export interface IUserPersistence {
    _id: string;
    email: string;
    username: string;
    password: string;
    name: string;
    birthDate: string;
    acceptedTerms: boolean;
    isDataAdmin: boolean;
}