import { ReactNode } from "react";
import { LoginData } from "../../utils/request";

export interface IUser
{
    id?: number;
    nickname? : string;
    email?: string;
}

export interface IContext extends IUser
{
    userAuthenticated: boolean;

    isLoading : boolean;
    
    authenticate: (loginData: LoginData) => Promise<void>;
    
    logout: () => void;
}

export interface IAuthProvider
{
    children : ReactNode;
}