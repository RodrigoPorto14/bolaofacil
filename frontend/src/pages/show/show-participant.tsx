import Header from "../../components/header/header"
import MenuLayout from "../../components/menu/menu-layout"
import MenuItem from "../../components/menu/menu-item"
import OverflowContainer from "../../components/menu/overflow-container"
import BackButton from "../../components/buttons/button-back"
import { ConfigItems } from "../../utils/nav-items"
import { useParams } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../../utils/request"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUpLong, faBan, faDownLong } from '@fortawesome/free-solid-svg-icons'
import ConfirmModal from "../../components/modal/ConfirmModal"

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
    const [participantId, setParticipantId] = useState(0);

    const baseUrl = `/boloes/${sweepstakeId}/participantes`

    const [promoteModal, setPromoteModal] = useState(false);
    const [demoteModal, setDemoteModal] = useState(false);
    const [banModal, setBanModal] = useState(false);

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

    function onPromote() { setPromoteModal(false); onChangeRole('ADMIN'); }
    function onDemote() { setDemoteModal(false); onChangeRole('PLAYER'); }

    function onChangeRole(role : string)
    {
        const data = { role }
        makePrivateRequest({url: baseUrl+'/'+participantId, data,  method: 'PUT'})
            .then(response =>
            {
                fetchParticipants();
            })
            .catch(error => console.log(error))    
    }

    const onDelete = () =>
    {
        setBanModal(false);
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

            <ConfirmModal 
                question="Deseja promover o participante para ADMIN?"
                isOpen={promoteModal} 
                onRequestClose={() => { setPromoteModal(false); }}
                onAccept={onPromote}
            />

            <ConfirmModal 
                question="Deseja rebaixar o participante para PLAYER?"
                isOpen={demoteModal} 
                onRequestClose={() => { setDemoteModal(false); }}
                onAccept={onDemote}
            />

            <ConfirmModal 
                question="Deseja remover este participante?"
                isOpen={banModal} 
                onRequestClose={() => { setBanModal(false); }}
                onAccept={onDelete}
            />

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
                                        onClick={() => { setParticipantId(participant.userId); setPromoteModal(true); } }
                                        data-tooltip-id="tooltip" 
                                        data-tooltip-content="Promover" 
                                    />
                                }
                                
                                {
                                    participant.role === "ADMIN" &&
                                    <FontAwesomeIcon 
                                        className="text-xl hover:text-red-500 hover:cursor-pointer" 
                                        icon={faDownLong}
                                        onClick={() => { setParticipantId(participant.userId); setDemoteModal(true); } }
                                        data-tooltip-id="tooltip" 
                                        data-tooltip-content="Rebaixar"
                                    />
                                }
                                
                                <FontAwesomeIcon 
                                    className="text-xl hover:text-red-500 hover:cursor-pointer" 
                                    icon={faBan}
                                    onClick={() => { setParticipantId(participant.userId); setBanModal(true); } }
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