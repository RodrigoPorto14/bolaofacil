import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import { configItems } from "../utils/nav-items"
import { useParams, useNavigate } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../utils/request"
import MenuItem from "../components/menu-item"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'

type RequestSample = 
{
    userId: number;
    sweepstakeId : number;
    userNickname : string;
}

const ShowRequest = () =>
{
    const { sweepstakeId } = useParams();
    const [requests, setRequests] = useState<RequestSample[]>([]);
    const navigate = useNavigate();

    const baseUrl = `/boloes/${sweepstakeId}/`

    useEffect(() =>
    {
        makePrivateRequest( {url: baseUrl+'requests' } )
            .then((response) =>
            {
                setRequests(response.data)
            })
            .catch((error) => console.log(error))

    },[baseUrl])

    const onAccept = (request : RequestSample) =>
    {
        makePrivateRequest({ url: baseUrl+`participantes/${request.userId}`, method: 'POST'})
            .then(response =>
            {
                onDelete(request);
            })
            .catch(error => console.log(error))
    }

    const onDelete = (request : RequestSample) =>
    {
        makePrivateRequest({url: baseUrl+`requests/${request.userId}`, method: 'DELETE'})
            .then(response =>
            {
                navigate(0);
            })
            .catch(error => console.log(error))
    }

    return(

        <>
            <Header status='logged' />

            <MenuLayout navItems={configItems(sweepstakeId)}>

                <div className="flex flex-col gap-2 overflow-y-auto">
                {
                    requests.map((request) =>
                    (
                        <MenuItem key={request.sweepstakeId}>

                            <p>{request.userNickname}</p>

                            <div className="flex gap-3 items-center">

                                <FontAwesomeIcon
                                    onClick={() => onAccept(request)}
                                    icon={faCheck}
                                    className="text-2xl hover:text-brand-200 hover:cursor-pointer"
                                />

                                <FontAwesomeIcon 
                                    onClick={() => onDelete(request)}
                                    icon={faXmark}
                                    className="text-2xl hover:text-red-500 hover:cursor-pointer"
                                />
                                
                            </div>
                            
                        </MenuItem>

                    ))
                }
                </div>

            </MenuLayout>
        </>
    )
}

export default ShowRequest