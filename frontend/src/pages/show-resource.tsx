import Header from "../components/header/header"
import MenuLayout from "../components/menu/menu-layout"
import MenuItemLayout from "../components/menu/menu-item-layout"
import OwnerIcon from "../components/sweepstake/owner-icon"
import { menuItems, ConfigItems } from "../utils/nav-items"
import { useParams, useLocation } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../utils/request"
import MenuItem from "../components/menu/menu-item"
import { ResourceProps, ResourceSample } from "../utils/type"
import { toBrDate } from "../utils/date-handler"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCalendar } from "@fortawesome/free-solid-svg-icons"

const ShowResource = ({ resource } : ResourceProps) =>
{

    const isSweepstake = resource === "sweepstakes";
    const isMatch = resource === "partidas";
    const { sweepstakeId } = useParams();
    const [resources, setResources] = useState<ResourceSample[]>([]);
    const location = useLocation()

    const buttonName = isSweepstake ? "NOVO BOLÃO" : "ADICIONAR";

    const url = isSweepstake ? "/boloes" : `/boloes/${sweepstakeId}/${resource}`

    const navItems = isSweepstake ? menuItems : ConfigItems(sweepstakeId);

    useEffect(() =>
    {
        makePrivateRequest( { url } )
            .then((response) =>
            {
                console.log(response.data)
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
                                { isSweepstake && <OwnerIcon ownerName={resource.ownerName} /> }
                                    
                                { 
                                    isMatch && 
                                    <>
                                        <p className="hidden md:block"> {toBrDate(resource.startMoment as string)} </p> 
                                        <FontAwesomeIcon 
                                            className="md:hidden" 
                                            icon={faCalendar}
                                            data-tooltip-id="tooltip" 
                                            data-tooltip-content={toBrDate(resource.startMoment as string)}
                                        />
                                    </>
                                }
                                
                            </MenuItem>

                        ))
                    }
                
                </MenuItemLayout>

            </MenuLayout>
        </>
    )
}

export default ShowResource