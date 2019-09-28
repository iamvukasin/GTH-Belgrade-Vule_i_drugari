import flask
from flask import Response

import json

import tequila

app = flask.Flask('your_flask_env')

SOURCE_PARAMETER = 'src'
DESTINATION_PARAMETER = 'dst'
DATE_FROM_PARAMETER = 'from_date'
DATE_TO_PARAMETER = 'to_date'
NUM_PEOPLE_PARAMETER = 'num_people'
NUM_CHILDREN_PARAMETER = 'num_children'
PETS_PARAMETER = 'pets'

AIRLINES = ['JU', 'LH', 'AF']

def get_param(param_name):
    if(flask.request.values.has_key(param_name)):
        return flask.request.values.get(param_name)
    
    return ""

def get_flight_airline(flight, dict):
    for air in flight[dict]:
        for aline in AIRLINES:
            if(air == aline):
                return air
    return flight[dict][0]

def process_pet(iata, pet):
    type = pet['type'].lower()
    breed = pet['breed'].lower()

    age = 0
    try:
        age = int(pet['age'])
    except:
        age = 0

    weight = 0
    try:
        weight = float(pet['weight'])
    except:
        weight = 0

    #TODO refactor

    if(iata == 'JU'):
        if(type == 'dog' or type == 'cat'):
            if(age < 12):
                return "Pet too young to fly!"
            else:
                if(weight > 8):
                    return "Airline don't support pets above 8kg"
                else:
                    return 45
        else:
            return "Pet not supported by airline!"
    elif(iata == 'LH'):
        if(type == 'dog' or type == 'cat'):
            if(age < 12):
                return "Pet too young to fly!"
            else:
                if(weight > 8):
                    return 200
                else:
                    return 100
        elif(type == 'rabbit'):
            if(age < 5):
                return "Pet too young to fly!"
            else:
                return 100
        else:
            return "Pet not supported by airline!"
    elif(iata == 'AF'):
        if(type == 'dog' and breed == 'pitbull'):
            return "Breed not permited in AirFrance!"
        else:
            if(weight > 75):
                return 100
            else:
                return 55
    else:
        if(type == 'dog' or type == 'cat'):
            if(age < 12):
                return "Pet too young to fly!"
            else:
                if(weight > 8):
                    return 500
                else:
                    return 250
        else:
            return 300

    return 0

@app.route('/get', methods=['GET'])
def get():
    if flask.request.method == 'GET':
        source = get_param(SOURCE_PARAMETER)
        destination = get_param(DESTINATION_PARAMETER)
        date_from = get_param(DATE_FROM_PARAMETER)
        date_to = get_param(DATE_TO_PARAMETER)
        num_people = get_param(NUM_PEOPLE_PARAMETER)
        num_children = get_param(NUM_CHILDREN_PARAMETER)
        pets = get_param(PETS_PARAMETER)

        flights = tequila.find_flight(source, destination, date_from, date_to, num_people, num_children)

        try:
            pets = json.loads(pets)
        except:
            return "no pets"

        prices_w_and_wo_pets = []

        for flight in flights:
            print(flight)
            price = flight["price"][next(iter(flight["price"]))]
            pet_price = 0
            pets_not_flying = 0
            reason = ""
            
            flg = [get_flight_airline(flight, 'airlines_from'), get_flight_airline(flight, 'airlines_return')]

            pets_not_flying_arr = [False] * len(pets)

            for i in range(len(pets)):
                pet = pets[i]
                for f in flg:
                    tmp = process_pet(f, pet)
                    try:
                        pet_price += int(tmp)
                    except ValueError:
                        reason = tmp
                        pets_not_flying_arr[i] = True
                
            for i in range(len(pets)):
                if(pets_not_flying_arr[i]):
                    pets_not_flying += 1

            prices_w_and_wo_pets.append((price, pet_price, pets_not_flying, flight, price + pet_price, reason))

        ret = "["

        prices_w_and_wo_pets.sort(key=lambda tup: tup[4])

        for i in range(len(prices_w_and_wo_pets)):

            price = prices_w_and_wo_pets[i]

            flg_to = get_flight_airline(price[3], 'airlines_return')
            flg_from = get_flight_airline(price[3], 'airlines_from')

            flg_date_from = date_from
            flg_date_to = date_to
            
            flg_time_to = price[3]['duration_return']
            flg_time_from = price[3]['duration_from']

            obj = "{\"iata_to\":\"" + flg_to + "\","
            obj += "\"iata_from\":\"" + flg_from + "\","
            obj += "\"flight_date_from\":\"" + flg_date_from + "\","
            obj += "\"flight_time_from\":\"" + flg_time_from + "\","
            obj += "\"flight_date_to\":\"" + flg_date_to + "\","
            obj += "\"flight_time_to\":\"" + flg_time_to + "\","
            obj += "\"stops_from\":" + str(len(price[3]['airlines_from']) - 1) + ","
            obj += "\"stops_to\":" + str(len(price[3]['airlines_return']) - 1) + ","
            obj += "\"link\":\"" + price[3]['link'] + "\","
            obj += "\"price\":" + str(price[0]) + ","
            obj += "\"price_with_pets\":" + str(price[1]) + ","
            obj += "\"pets_not_flying\":" + str(price[2]) + ","
            obj += "\"reason\":\"" + price[5] + "\"}"

            ret += obj + ","

        ret = ret[:-1]
        ret += "]"

        return Response(ret, mimetype='application/json')