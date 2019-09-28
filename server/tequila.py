import requests
import datetime

def create_time_string(seconds):
    hours = seconds // 3600
    minutes = (seconds % 3600) // 60
    return '{}h {}min'.format(hours, minutes) if minutes != 0 else '{}h'.format(hours)

def find_flight(location_from, location_to, date_from, date_to = None, num_adults = 1, num_children = 0, round=True):
    """Returns list of flights with given parameters

    Args:
        location_from (str): IATA code of departing airport
        location_to (str): IATA code of arriving airport
        date_from (str): date of departing format: DD/MM/YYYY
        date_to (str): date of arriving format: DD/MM/YYYY
        num_adults (int): number of adults traveling
        num_children (int): number of children traveling


    Returns:
        list: a list of dictionaries containing airlines, link and price
    """
    url = 'https://kiwicom-prod.apigee.net/v2/search'
    params = {
        'apikey' : 'sUjIKIVbdlm8cseNdWGsxUAVCBs47Mjh',
        'fly_from' : location_from,
        'fly_to' : location_to,
        'date_from' : date_from,
        'date_to' : date_from,
        'return_from' : date_to,
        'return_to' : date_to,
        'adults' : num_adults,
        'children' : num_children,
        'select_airlines' : 'LH,JU,TK,AF,TP',
        'max_stopovers': 2,
        'flight_type': 'round' if round else 'oneway'
    }

    response = requests.get(url, params = params)
    json_response = response.json()

    result = []
    data = json_response['data']

    for option in data[:10]:
        price = option['conversion']
        link = option['deep_link']

        airlinesFrom = []
        airlinesReturn = []

        route = option['route']
        for flight in route:
            if flight['return'] == 0:
                airlinesFrom.append(flight['airline'])
            else:
                airlinesReturn.append(flight['airline'])

        duration_from = create_time_string(option['duration']['departure'])
        duration_return = create_time_string(option['duration']['return'])

        result.append({'price' : price, 'link': link,
                       'airlines_from' : airlinesFrom, 'airlines_return' : airlinesReturn,
                       'duration_from' : duration_from,
                       'duration_return' : duration_return})

    return result

if __name__ == '__main__':
    print(find_flight('BEG', 'TXL', '10/11/2019', num_adults=2, round=False))
    print(find_flight('BEG', 'TXL','10/11/2019', '12/11/2019', num_adults=2, round=True))
