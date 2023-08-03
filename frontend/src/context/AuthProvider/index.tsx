import { ReactNode, createContext, useState, useEffect } from "react";
import { LoginData, makeLogin, makePrivateRequest } from "../../utils/request";
import { IContext, IUser } from "./types";
import { saveToken, removeToken, getToken } from "./auth";

const AuthContext = createContext<IContext>({} as IContext);

const AuthProvider = ({ children } : {children : ReactNode}) =>
{
    const [user, setUser] = useState<IUser | null>(null);
    const [userAuthenticated, setUserAuthenticated] = useState(false);
    const [isLoading, setLoading] = useState(true);

    useEffect(() =>
    {
        if(getToken())
            makePrivateRequest({url : '/users'})
                .then(response =>
                {
                    setUserAuthenticated(true)
                    setUser(response.data);
                    setLoading(false);
                })
                .catch(() => { logout(); setLoading(false); })
        else
            setLoading(false);

    },[])

    const authenticate =  async (loginData : LoginData) => 
    {
        await makeLogin(loginData)
                .then(response =>
                {  
                    const data = response.data;
                    saveToken(data.access_token);
                    setUserAuthenticated(true)
                    setUser({id : data.userId, nickname: data.userNickname, email: data.userEmail});
                    setLoading(false);
        

                })
                .catch(error => Promise.reject(error))
    }

    const logout = () =>
    {
        removeToken();
        setUserAuthenticated(false)
        setUser(null);
    }

    return(

        <AuthContext.Provider value={{...user, isLoading, userAuthenticated, authenticate, logout}}>
            {children}
        </AuthContext.Provider>

    )
}

export { AuthContext, AuthProvider}