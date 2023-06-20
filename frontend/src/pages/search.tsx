import Header from "../components/header"
import MenuLayout from "../components/menu-layout"
import MenuItem from "../components/menu-item"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass, faLock, faLockOpen } from '@fortawesome/free-solid-svg-icons'
import { menuItems } from "../utils/nav-items"
import { Sweepstake } from '../utils/type'
import { useState } from "react"
import { makePrivateRequest } from "../utils/request"

const Search = () =>
{
    const [inputValue, setInputValue] = useState('');
    const [sweepstakes, setSweepstakes] = useState<Sweepstake[]>([]);

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
                  

                    <div className="flex flex-col h-4/5 gap-2 overflow-y-auto ">

                        {   
                            sweepstakes.map((sweepstake) =>
                            (
                                <MenuItem redirect='/' key={sweepstake.id}>
                                    <div className="flex items-center gap-2">
                                        { sweepstake.private_ ? <FontAwesomeIcon icon={faLock} /> : <FontAwesomeIcon icon={faLockOpen} /> }
                                        <p>{sweepstake.name}</p>
                                    </div>
                                    
                                    <p>{`Criado por: ${sweepstake.ownerName}`}</p>
                                </MenuItem>
                            ))
                        }

                    </div>

            </MenuLayout>
        </>
        
    )
}

export default Search