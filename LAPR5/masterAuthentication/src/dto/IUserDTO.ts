export default interface IUserDTO {
    email: string;
    username: string;
    password?:string;
    name: string;
    birthDate: string;
    acceptedTerms?: boolean;
    isDataAdmin: boolean;
}