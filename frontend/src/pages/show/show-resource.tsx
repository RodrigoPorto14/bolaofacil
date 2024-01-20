import Header from "../../components/header/header"
import MenuLayout from "../../components/menu/menu-layout"
import MenuItemLayout from "../../components/menu/menu-item-layout"
import OwnerIcon from "../../components/sweepstake/owner-icon"
import { menuItems, ConfigItems } from "../../utils/nav-items"
import { useParams, useLocation } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../../utils/request"
import MenuItem from "../../components/menu/menu-item"
import { ResourceProps, ResourceSample } from "../../utils/types"
import { toBrFormatDate } from "../../utils/date-handler"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCalendar } from "@fortawesome/free-solid-svg-icons"
import { _isMatch, _isSweepstake } from "../../utils/enums"

const ShowResource = ({ resource } : ResourceProps) =>
{

    const isSweepstake = _isSweepstake(resource);
    const isMatch = _isMatch(resource);
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
                                        <p className="hidden md:block"> {toBrFormatDate(resource.startMoment as string)} </p> 
                                        <FontAwesomeIcon 
                                            className="md:hidden" 
                                            icon={faCalendar}
                                            data-tooltip-id="tooltip" 
                                            data-tooltip-content={toBrFormatDate(resource.startMoment as string)}
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