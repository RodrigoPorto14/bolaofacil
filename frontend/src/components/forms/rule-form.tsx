import { useForm } from 'react-hook-form';
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import Input from "../inputs/input";
import InputNumber from "../inputs/input-number";
import FormLayout from './form-layout';
import { Rule, FormProps } from '../../utils/type';




const RuleForm = ({ onSubmit, buttonName, resource, onDelete, create=false } : FormProps) =>
{
    const requiredError = {invalid_type_error: "Campo obrigatório"};

    const numberValidation = z.number(requiredError)
                              .min(0,'Deve ser maior ou igual a 0')
                              .max(99,'Deve ser menor ou igual a 99')

    const ruleFormSchema = z.object(
    {
        name: z.string()
            .nonempty('Campo Obrigatório')
            .max(20, 'Deve conter no máximo 20 caracteres'),
        
        exactScore: numberValidation,
        winnerScore: numberValidation,
        scoreDifference: numberValidation,
        loserScore: numberValidation,
        winner: numberValidation,
    })

    type RuleFormData = z.infer<typeof ruleFormSchema>

    const { register, handleSubmit, formState : { errors } } = useForm<RuleFormData>({resolver: zodResolver(ruleFormSchema)});

    return(
            <FormLayout onSubmit={handleSubmit(onSubmit)} buttonName={buttonName} onDelete={onDelete} create={create} resource={resource} >

                <Input<RuleFormData>
                    label="Nome da Regra"
                    type="text"
                    name="name"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.name}
                />

                <InputNumber<RuleFormData>
                    label="Placar Exato"
                    name="exactScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.exactScore}
                    tooltip="Pontos ganhos ao acertar o placar exato"
                />

                <InputNumber<RuleFormData>
                    label="Placar do Vencedor"
                    name="winnerScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.winnerScore}
                    tooltip="Pontos ganhos ao acertar o placar do vencedor"
                />

                <InputNumber<RuleFormData>
                    label="Diferença do Placar"
                    name="scoreDifference"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.scoreDifference}
                    tooltip="Pontos ganhos ao acertar a diferença entre os placares"
                    
                />

                <InputNumber<RuleFormData>
                    label="Placar do Perdedor"
                    name="loserScore"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.loserScore}
                    tooltip="Pontos ganhos ao acertar o vencedor e o placar do perdedor"
                />

                <InputNumber<RuleFormData>
                    label="Vencedor"
                    name="winner"
                    register={register}
                    errors={errors}
                    defaultValue={(resource as Rule)?.winner}
                    tooltip="Pontos ganhos ao acertar o vencedor da partida"
                />                                      
                        
            </FormLayout>
    )
}

export default RuleForm