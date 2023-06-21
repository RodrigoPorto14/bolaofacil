import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import MenuItem from "../components/menu-item"
import OverflowContainer from "../components/overflow-container"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass, faLock, faLockOpen, faRightFromBracket, faEnvelope } from '@fortawesome/free-solid-svg-icons'
import { menuItems } from "../utils/nav-items"
import { Sweepstake } from '../utils/type'
import { useState } from "react"
import { makePrivateRequest } from "../utils/request"
import { useNavigate } from "react-router-dom"

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
                console.log(response.data)
                navigate('/sweepstakes')
            })
            .catch(error => console.log(error))
    }

    const onRequest = (sweepstakeId : number) =>
    {
        makePrivateRequest({url: `boloes/${sweepstakeId}/requests`, method: 'POST'})
            .then(response =>
            {
                console.log(response.data)
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
                                    
                                    <div className="flex items-center gap-4">

                                        <p>{`Criado por: ${sweepstake.ownerName}`}</p>
                                        { 
                                            sweepstake.private_ ? 
                                            <FontAwesomeIcon 
                                                className="text-xl hover:text-brand-200 hover:cursor-pointer"
                                                icon={faEnvelope}
                                                onClick={() => onRequest(sweepstake.id)}
                                            /> : 
                                            <FontAwesomeIcon
                                                className="text-xl hover:text-brand-200 hover:cursor-pointer"
                                                icon={faRightFromBracket}
                                                onClick={() => onEnter(sweepstake.id)} 
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