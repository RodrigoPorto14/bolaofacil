import { ReactNode, createContext, useState, useEffect } from "react";
import { IContext, IParticipant } from "./types";
import { Routes, useParams } from "react-router-dom";
import { makePrivateRequest } from "../../utils/request";

const ParticipantContext = createContext<IContext>({} as IContext);

const ParticipantProvider = ({ children } : {children : ReactNode}) =>
{
    const [participant, setParticipant] = useState<IParticipant | null>(null);
    const { sweepstakeId } = useParams();

    useEffect(() =>
    {
        makePrivateRequest({url : `/boloes/${sweepstakeId}/participantes/authenticated`})
            .then(response =>
            {
                const data = response.data;
                setParticipant({sweepstakeName: data.sweepstakeName, customSweepstake: data.custom, role: data.role })
            })
            .catch(error => console.log(error))

    },[])

    const isOwner = () => participant?.role === "OWNER";
    const isOwnerOrAdmin = () => isOwner() || participant?.role === "ADMIN";

    return(

        <ParticipantContext.Provider value={{...participant, isOwner, isOwnerOrAdmin}}>
            <Routes>
                {children}
            </Routes>
        </ParticipantContext.Provider>

    )
}

export { ParticipantContext, ParticipantProvider}