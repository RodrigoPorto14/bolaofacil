import { ResourceSample } from "../../utils/type";
import ErrorMessage from "./error-message";

type SelectOptionsProps<T> = 
{
    label : string;
    name: keyof T;
    resources : ResourceSample[] | undefined;
    register : any;
    value : keyof ResourceSample;
    errors : any;
    defaultValue? : string | number;
    width? : string;
}

function SelectOptions<T>({ label, name, resources, register, value, errors, defaultValue, width=''} : SelectOptionsProps<T>)
{
    return(
        <>
        {
            resources &&
            (
                <div className={`flex flex-col ${width}`}>

                    <label className="px-1">{label}</label>

                    <select defaultValue={defaultValue} className="rounded-lg h-7 px-2" name={name} {...register(name)}>
                    {
                        resources.map((resource) => <option  key={resource.id} value={resource[value]}> {resource.name} </option> )
                    }
                    </select>
                    { errors[name] && <ErrorMessage> {errors[name].message} </ErrorMessage> }

                </div>
            )
        }
        </>
    )
}

export default SelectOptions;