import Header from "../components/header/header"
import MenuLayout from "../components/menu/menu-layout"
import MenuItem from "../components/menu/menu-item"
import OverflowContainer from "../components/menu/overflow-container"
import OwnerIcon from "../components/sweepstake/owner-icon"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass, faLock, faLockOpen, faRightFromBracket, faEnvelope, faEnvelopeCircleCheck } from '@fortawesome/free-solid-svg-icons'
import { menuItems } from "../utils/nav-items"
import { Sweepstake } from '../utils/types'
import { useState } from "react"
import { makePrivateRequest } from "../utils/request"
import { useNavigate } from "react-router-dom"
import { toast } from "react-toastify"

const Search = () =>
{
    const [inputValue, setInputValue] = useState('');
    const [sweepstakes, setSweepstakes] = useState<Sweepstake[]>([]);
    const navigate = useNavigate();

    const handleSendSearch = () =>
    {
        const trimmedSearch = inputValue.trim()

        if(trimmedSearch)
        {
            makePrivateRequest({url: '/boloes/busca', params: {name: trimmedSearch}})
            .then((response) =>
            {
                setSweepstakes(response.data)
            })
            .catch((error) => {console.log(error)})
        }
    }

    const handleInputChange = (event: { target: { value: React.SetStateAction<string> } }) => setInputValue(event.target.value);

    const handleKeyDown = (event: { key: string; preventDefault: () => void }) => 
    {
        if (event.key === 'Enter') 
        {
            event.preventDefault();
            handleSendSearch();
        }
    };

    const onEnter = (sweepstakeId : number) =>
    {
        makePrivateRequest({url: `boloes/${sweepstakeId}/participantes`, method: 'POST'})
            .then(response =>
            {
                navigate('/sweepstakes')
            })
            .catch(error => console.log(error))
    }

    const onRequest = (sweepstakeId : number) =>
    {
        makePrivateRequest({url: `boloes/${sweepstakeId}/requests`, method: 'POST'})
            .then(response =>
            {
                toast.success("Pedido enviado!")
                handleSendSearch();
            })
            .catch(error => console.log(error))
    }

    return(

        <>
            <Header status='logged' />

            <MenuLayout justify="justify-start" navItems={menuItems} >
            
                    <div className="relative h-8 w-4/5 mx-auto">
                        <input
                            type="text"
                            placeholder="Pesquise pelo nome ou dono do bolão aqui"
                            className="w-full h-full rounded-lg px-2"
                            value={inputValue}
                            onChange={handleInputChange}
                            onKeyDown={handleKeyDown}
                        />
                        <FontAwesomeIcon
                            icon={faMagnifyingGlass}
                            className="absolute inset-y-1 right-0 mt-1 mr-2 text-gray-500"
                        />
                    </div>
                  
                    <OverflowContainer>

                        {   
                            sweepstakes.map((sweepstake) =>
                            (
                                <MenuItem key={sweepstake.id}>
                                    <div className="flex items-center gap-2">
                                        { sweepstake.private_ ? <FontAwesomeIcon icon={faLock} /> : <FontAwesomeIcon icon={faLockOpen} /> }
                                        <p>{sweepstake.name}</p>
                                    </div>
                                    
                                    <div className="flex items-center gap-2">

                                        <OwnerIcon ownerName={sweepstake.ownerName} />
                                        { 
                                            sweepstake.private_ ? 
                                            <FontAwesomeIcon 
                                                className={`text-xl ${sweepstake.hasRequest ? "" : "hover:text-brand-200 hover:cursor-pointer"}`}
                                                icon={sweepstake.hasRequest ? faEnvelopeCircleCheck : faEnvelope}
                                                onClick={sweepstake.hasRequest ? undefined : () => onRequest(sweepstake.id)}
                                                data-tooltip-id="tooltip" 
                                                data-tooltip-content={sweepstake.hasRequest ? "Pedido enviado" : "Pedir para entrar"}
                                            />

                                            : 

                                            <FontAwesomeIcon
                                                className="text-xl hover:text-brand-200 hover:cursor-pointer"
                                                icon={faRightFromBracket}
                                                onClick={() => onEnter(sweepstake.id)} 
                                                data-tooltip-id="tooltip" 
                                                data-tooltip-content="Entrar"
                                            />                                            
                                        }

                                    </div>
                                    
                                </MenuItem>
                            ))
                        }

                    </OverflowContainer>

            </MenuLayout>
        </>
        
    )
}

export default Search