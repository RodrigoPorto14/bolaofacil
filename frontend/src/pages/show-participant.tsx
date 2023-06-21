import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import MenuItem from "../components/menu-item"
import OverflowContainer from "../components/overflow-container"
import { configItems } from "../utils/nav-items"
import { useParams, useNavigate } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../utils/request"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUpLong, faBan, faDownLong } from '@fortawesome/free-solid-svg-icons'

type ParticipantSample = 
{
    userId: number;
    userNickname : string;
    role: string
}

const ShowParticipant = () =>
{

    const { sweepstakeId } = useParams();
    const [participants, setParticipants] = useState<ParticipantSample[]>([]);
    const navigate = useNavigate();

    const baseUrl = `/boloes/${sweepstakeId}/participantes`

    useEffect(() =>
    {
        makePrivateRequest( {url: baseUrl } )
            .then((response) =>
            {
                setParticipants(response.data)
            })
            .catch((error) => console.log(error))

    },[baseUrl])

    const onChangeRole = (participant : ParticipantSample, newRole : string) =>
    {
        const data = {role: newRole}
        makePrivateRequest({url: baseUrl+'/'+participant.userId, data,  method: 'PUT'})
            .then(response =>
            {
                navigate(0)
            })
            .catch(error => console.log(error))
    }

    const onDelete = (participantId : number) =>
    {
        makePrivateRequest({url: baseUrl+'/'+participantId,  method: 'DELETE'})
            .then(response =>
            {
                navigate(0)
            })
            .catch(error => console.log(error))
    }

    const roleColor = new Map<string, string>(
    [
        ["PLAYER", "text-green-600"],
        ["ADMIN", "text-blue-600"],
    ]);

    return(

        <>
            <Header status='logged' />

            <MenuLayout navItems={configItems(sweepstakeId)}>

                <OverflowContainer>
                {
                    participants.map((participant) =>
                    (
                        <MenuItem key={participant.userId}>
                            <p>{participant.userNickname}</p>

                            <div className="flex gap-3 items-center">

                                {
                                    participant.role === "PLAYER" &&
                                    <FontAwesomeIcon 
                                        className="text-xl hover:text-brand-200 hover:cursor-pointer" 
                                        icon={faUpLong}
                                        onClick={() => onChangeRole(participant,"ADMIN")} 
                                    />
                                }
                                
                                {
                                    participant.role === "ADMIN" &&
                                    <FontAwesomeIcon 
                                        className="text-xl hover:text-red-500 hover:cursor-pointer" 
                                        icon={faDownLong}
                                        onClick={() => onChangeRole(participant,"PLAYER")}
                                    />
                                }
                                
                                <FontAwesomeIcon 
                                    onClick={() => onDelete(participant.userId)}
                                    className="text-xl hover:text-red-500 hover:cursor-pointer" 
                                    icon={faBan}
                                />

                                <p className={`font-bold ${roleColor.get(participant.role)}`}>{participant.role}</p>
                            </div>
                            
                        </MenuItem>

                    ))
                }
                </OverflowContainer>

            </MenuLayout>
        </>
    )
}

export default ShowParticipant