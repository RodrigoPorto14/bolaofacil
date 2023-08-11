import Header from "../components/header/header"
import MenuLayout from "../components/menu/menu-layout"
import MenuItem from "../components/menu/menu-item"
import OverflowContainer from "../components/menu/overflow-container"
import BackButton from "../components/buttons/button-back"
import { ConfigItems } from "../utils/nav-items"
import { useParams } from "react-router-dom"
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

    const baseUrl = `/boloes/${sweepstakeId}/participantes`

    useEffect(() =>
    {
        fetchParticipants();

    },[])

    const fetchParticipants = () =>
    {
        makePrivateRequest( {url: baseUrl } )
            .then((response) =>
            {
                setParticipants(response.data)
            })
            .catch((error) => console.log(error))
    }

    const onChangeRole = (participant : ParticipantSample, newRole : string) =>
    {
        const data = {role: newRole}
        makePrivateRequest({url: baseUrl+'/'+participant.userId, data,  method: 'PUT'})
            .then(response =>
            {
                fetchParticipants();
            })
            .catch(error => console.log(error))
    }

    const onDelete = (participantId : number) =>
    {
        makePrivateRequest({url: baseUrl+'/'+participantId,  method: 'DELETE'})
            .then(response =>
            {
                fetchParticipants();
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

            <MenuLayout navItems={ConfigItems(sweepstakeId)}>

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
                                        data-tooltip-id="tooltip" 
                                        data-tooltip-content="Promover" 
                                    />
                                }
                                
                                {
                                    participant.role === "ADMIN" &&
                                    <FontAwesomeIcon 
                                        className="text-xl hover:text-red-500 hover:cursor-pointer" 
                                        icon={faDownLong}
                                        onClick={() => onChangeRole(participant,"PLAYER")}
                                        data-tooltip-id="tooltip" 
                                        data-tooltip-content="Rebaixar"
                                    />
                                }
                                
                                <FontAwesomeIcon 
                                    onClick={() => onDelete(participant.userId)}
                                    className="text-xl hover:text-red-500 hover:cursor-pointer" 
                                    icon={faBan}
                                    data-tooltip-id="tooltip" 
                                    data-tooltip-content="Expulsar"
                                />

                                <p className={`font-bold ${roleColor.get(participant.role)}`}>{participant.role}</p>
                            </div>
                            
                        </MenuItem>

                    ))
                }
                </OverflowContainer>

                <div className="mx-auto">
                    <BackButton />
                </div>
                

            </MenuLayout>
        </>
    )
}

export default ShowParticipant