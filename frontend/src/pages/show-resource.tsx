import Header from "../components/header/header"
import MenuLayout from "../components/menu/menu-layout"
import MenuItemLayout from "../components/menu/menu-item-layout"
import { menuItems, configItems } from "../utils/nav-items"
import { useParams, useLocation } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../utils/request"
import MenuItem from "../components/menu/menu-item"
import { ResourceProps, ResourceSample } from "../utils/type"
import { useParticipant } from "../context/ParticipantProvider/useParticipant"

const ShowResource = ({ resource } : ResourceProps) =>
{

    const isSweepstake = resource === "sweepstakes";
    const { sweepstakeId } = useParams();
    const [resources, setResources] = useState<ResourceSample[]>([]);
    const location = useLocation()
    const participant = useParticipant()

    const buttonName = isSweepstake ? "NOVO BOLÃO" : "ADICIONAR";

    const url = isSweepstake ? "/boloes" : `/boloes/${sweepstakeId}/${resource}`

    const navItems = isSweepstake ? menuItems : configItems(sweepstakeId, participant.role, participant.tournament);

    useEffect(() =>
    {
        makePrivateRequest( { url } )
            .then((response) =>
            {
                setResources(response.data)
            })
            .catch((error) => console.log(error))

    },[url])

    return(

        <>
            <Header status='logged' />

            <MenuLayout justify="justify-between" navItems={navItems}>

                <MenuItemLayout to={`${location.pathname}/create`} buttonName={buttonName} isSweepstake={isSweepstake}>

                    {
                        resources.map((resource) =>
                        (
                            <MenuItem redirect={`${location.pathname}/${resource.id}`} key={resource.id}>
                                <p>{resource.name}</p>
                                { isSweepstake && <p>{`Criado por: ${resource.ownerName}`}</p> }
                            </MenuItem>

                        ))
                    }
                
                </MenuItemLayout>

            </MenuLayout>
        </>
    )
}

export default ShowResource