import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import MenuItemLayout from "../components/menu-item-layout"
import MenuItem from "../components/menu-item"
import { menuItems } from "../utils/nav-items"
import { Sweepstake } from "../utils/type"
import { useEffect, useState } from "react"
import { makePrivateRequest } from "../utils/request"
import { useLocation } from "react-router-dom"

const SweepStakes = () =>
{

    const [sweepstakes, setSweepstakes] = useState<Sweepstake[]>([])
    const location = useLocation();

    useEffect(() =>
    {
        makePrivateRequest({url: '/boloes'})
            .then((response) =>
            {
                setSweepstakes(response.data)
            })
            .catch((error) => {console.log(error)})

    },[])

    return(

        <>
            <Header status='logged' />

            <MenuLayout justify="justify-between" navItems={menuItems} >

                <MenuItemLayout to="/" buttonName="NOVO BOLÃO">
                    {
                        sweepstakes.map((sweepstake) =>
                        (
                            <MenuItem redirect={`${location.pathname}/${sweepstake.id}/config/sweepstake`} key={sweepstake.id}>
                                <p>{sweepstake.name}</p>
                                <p>{`Criado por: ${sweepstake.ownerName}`}</p>
                            </MenuItem>
                        ))
                    }
                </MenuItemLayout>

            </MenuLayout>
        </>
        
    )
}

export default SweepStakes