import { ReactNode, createContext, useState, useEffect } from "react";
import { IParticipant } from "./types";
import { Routes, useParams } from "react-router-dom";
import { makePrivateRequest } from "../../utils/request";
import { useAuth } from "../AuthProvider/useAuth";

const ParticipantContext = createContext<IParticipant>({} as IParticipant);

const ParticipantProvider = ({ children } : {children : ReactNode}) =>
{
    const [participant, setParticipant] = useState<IParticipant | null>(null);
    const { sweepstakeId } = useParams();
    const auth = useAuth();

    useEffect(() =>
    {
        console.log("BBBBB")
        makePrivateRequest({url : `/boloes/${sweepstakeId}/participantes/${auth.id}`})
            .then(response =>
            {
                const data = response.data;
                setParticipant({sweepstakeName: data.sweepstakeName, tournament: data.tournament, role: data.role })
            })
            .catch(error => console.log(error))

    },[])

    return(

        <ParticipantContext.Provider value={{...participant}}>
            <Routes>
                {children}
            </Routes>
        </ParticipantContext.Provider>

    )
}

export { ParticipantContext, ParticipantProvider}