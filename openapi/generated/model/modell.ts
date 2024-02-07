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
import { CollectionEntity } from './collectionEntity';
import { MapEntity } from './mapEntity';
import { ValueEntity } from './valueEntity';


export interface Modell { 
    id?: number;
    name?: string;
    street?: string;
    number?: number;
    age?: number;
    collectionValues?: Array<number>;
    mapValues?: { [key: string]: string; };
    valueEntity?: ValueEntity;
    collectionEntities?: Array<CollectionEntity>;
    mapEntities?: { [key: string]: MapEntity; };
}

