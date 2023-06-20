import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import MenuItemLayout from "../components/menu-item-layout"
import { configItems } from "../utils/nav-items"
import { useParams, useLocation } from "react-router-dom"
import { useState, useEffect } from "react"
import { makePrivateRequest } from "../utils/request"
import MenuItem from "../components/menu-item"
import { ResourceProps, ResourceSample } from "../utils/type"

const ShowResource = ({ resource } : ResourceProps) =>
{

    const buttonName = `ADICIONAR ${resource.slice(0,-1).toUpperCase()}`
    const { sweepstakeId } = useParams();
    const [resources, setResources] = useState<ResourceSample[]>([]);
    const location = useLocation()

    useEffect(() =>
    {
        makePrivateRequest( {url: `/boloes/${sweepstakeId}/${resource}`} )
            .then((response) =>
            {
                setResources(response.data)
            })
            .catch((error) => console.log(error))

    },[sweepstakeId,resource])

    return(

        <>
            <Header status='logged' />

            <MenuLayout justify="justify-between" navItems={configItems(sweepstakeId)}>

                <MenuItemLayout to={`${location.pathname}/create`} buttonName={buttonName}>

                    {
                        resources.map((resource) =>
                        (
                            <MenuItem redirect={`${location.pathname}/${resource.id}`} key={resource.id}>
                                <p>{resource.name}</p>
                            </MenuItem>

                        ))
                    }
                
                </MenuItemLayout>

            </MenuLayout>
        </>
    )
}

export default ShowResource