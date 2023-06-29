import { useContext } from "react"
import { ParticipantContext } from ".";

export const useParticipant = () =>
{
    const context = useContext(ParticipantContext)
    return context;
}