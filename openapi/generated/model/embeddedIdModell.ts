/**
 * quarkus API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { EmbeddedIdSubModell1 } from './embeddedIdSubModell1';
import { EmbeddedIdSubModell2 } from './embeddedIdSubModell2';
import { EmbeddedIdSubModell3 } from './embeddedIdSubModell3';
import { EmbeddedKeys } from './embeddedKeys';


export interface EmbeddedIdModell { 
    stringVal?: string;
    stringValNotNull?: string;
    booleanVal?: boolean;
    isBoolean?: boolean;
    booVal?: boolean;
    isBool?: boolean;
    integerVal?: number;
    intVal?: number;
    longVal?: number;
    longpVal?: number;
    bigIntegerVal?: number;
    keyA?: string;
    keyB?: string;
    keyC?: string;
    notUpdatable?: string;
    vStringVal?: string;
    virtualStringVal?: string;
    bool?: boolean;
    id?: EmbeddedKeys;
    key1?: string;
    key2?: string;
    key3?: string;
    embeddedIdSubModells1?: Array<EmbeddedIdSubModell1>;
    embeddedIdSubModells2?: Array<EmbeddedIdSubModell2>;
    embeddedIdSubModell3?: EmbeddedIdSubModell3;
}

