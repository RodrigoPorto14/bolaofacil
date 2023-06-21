import Button from "./button";

import { FormProps } from "../utils/type";

const FormLayout = ({children, onSubmit, buttonName, onDelete, resource, create=false, gap="gap-2"} : FormProps, ) =>
{
    return(
        <>
        {
            (create || resource) &&
            (
                <form noValidate onSubmit={onSubmit} className="flex flex-col h-full w-full justify-between">

                    <div className={`flex flex-col ${gap} items-start`}>

                        {children}
                                    
                    </div>
                                
                    <div className="flex gap-4 mx-auto">
                        <Button > {buttonName} </Button>
                        { onDelete && <Button onClick={onDelete}> DELETAR </Button> }
                    </div>

                </form>
            )    
        }
        </>  
    )
}

export default FormLayout;