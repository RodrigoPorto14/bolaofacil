import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import MenuItemLayout from "../components/menu-item-layout"
import { menuItems, configItems } from "../utils/nav-items"
import { useParams, useLocation } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../utils/request"
import MenuItem from "../components/menu-item"
import { ResourceProps, ResourceSample } from "../utils/type"

const ShowResource = ({ resource } : ResourceProps) =>
{

    const isSweepstake = resource === "sweepstakes";
    const { sweepstakeId } = useParams();
    const [resources, setResources] = useState<ResourceSample[]>([]);
    const location = useLocation()

    const buttonName = isSweepstake ? "NOVO BOLÃO" : `ADICIONAR ${resource.slice(0,-1).toUpperCase()}`

    const url = isSweepstake ? "/boloes" : `/boloes/${sweepstakeId}/${resource}`

    const navItems = isSweepstake ? menuItems : configItems(sweepstakeId);

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

                <MenuItemLayout to={`${location.pathname}/create`} buttonName={buttonName}>

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