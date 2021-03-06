import socket
from openpyxl import workbook,load_workbook
from xlutils.copy import copy
from datetime import timedelta,date
import re
count = 0
absent = 0
not_available = 0
nope = 0
now_count = 0
inverse_of_e = {'BD': '55', 'BE': '56', 'BF': '57', 'BG': '58', 'BA': '52', 'BB': '53', 'BC': '54', 'BL': '63', 'BM': '64', 'BN': '65', 'BO': '66', 'BH': '59', 'BI': '60', 'BJ': '61', 'BK': '62', 'BT': '71', 'BU': '72', 'BV': '73', 'BW': '74', 'BP': '67', 'BQ': '68', 'BR': '69', 'BS': '70', 'BX': '75', 'BY': '76', 'BZ': '77', 'CP': '93', 'D': '3', 'H': '7', 'L': '11', 'P': '15', 'T': '19', 'X': '23', 'FA': '156', 'A': '0', 'EO': '144', 'E': '4', 'EK': '140', 'I': '8', 'ED': '133', 'EG': '136', 'EM': '142', 'EL': '141', 'C': '2', 'EN': '143', 'EI': '138', 'EH': '137', 'G': '6', 'EJ': '139', 'EE': '134', 'M': '12', 'K': '10', 'EF': '135', 'EA': '130', 'O': '14', 'EB': '131', 'S': '18', 'EY': '154', 'EC': '132', 'W': '22', 'EZ': '155', 'EU': '150', 'ET': '149', 'EW': '152', 'EV': '151', 'EQ': '146', 'EP': '145', 'ES': '148', 'ER': '147', 'AQ': '42', 'AU': '46', 'EX': '153', 'FP': '171', 'FQ': '172', 'FR': '173', 'FS': '174', 'FT': '175', 'FU': '176', 'FV': '177', 'FW': '178', 'FX': '179', 'FY': '180', 'FZ': '181', 'Y': '24', 'FB': '157', 'FC': '158', 'FD': '159', 'FE': '160', 'FF': '161', 'FG': '162', 'FH': '163', 'FI': '164', 'FJ': '165', 'FK': '166', 'FL': '167', 'FM': '168', 'FN': '169', 'FO': '170', 'CK': '88', 'CJ': '87', 'CI': '86', 'CH': '85', 'CO': '92', 'CN': '91', 'CM': '90', 'F': '5', 'CC': '80', 'CB': '79', 'CA': '78', 'J': '9', 'CG': '84', 'CF': '83', 'CE': '82', 'N': '13', 'CZ': '103', 'CY': '102', 'R': '17', 'V': '21', 'CS': '96', 'CR': '95', 'CQ': '94', 'Z': '25', 'CW': '100', 'CV': '99', 'CU': '98', 'CT': '97', 'CD': '81', 'DN': '117', 'DO': '118', 'DL': '115', 'DM': '116', 'DJ': '113', 'DK': '114', 'DH': '111', 'DI': '112', 'DF': '109', 'DG': '110', 'DD': '107', 'DE': '108', 'DB': '105', 'DC': '106', 'DA': '104', 'B': '1', 'DZ': '129', 'DX': '127', 'DY': '128', 'DV': '125', 'DW': '126', 'DT': '123', 'DU': '124', 'DR': '121', 'DS': '122', 'DP': '119', 'DQ': '120', 'CL': '89', 'AA': '26', 'AC': '28', 'AB': '27', 'AE': '30', 'AD': '29', 'AG': '32', 'AF': '31', 'AI': '34', 'AH': '33', 'AK': '36', 'AJ': '35', 'AM': '38', 'AL': '37', 'AO': '40', 'AN': '39', 'Q': '16', 'AP': '41', 'AS': '44', 'AR': '43', 'U': '20', 'AT': '45', 'AW': '48', 'AV': '47', 'AY': '50', 'AX': '49', 'AZ': '51', 'CX': '101'}
new_data = {'2018-03-12 00:00:00': 'Y', '2018-04-01 00:00:00': 'DA', '2018-03-06 00:00:00': 'A', '2018-03-23 00:00:00': 'BQ', '2018-03-09 00:00:00': 'M', '2018-03-20 00:00:00': 'BE', '2018-04-07 00:00:00': 'DY', '2018-03-07 00:00:00': 'E', '2018-03-08 00:00:00': 'I', '2018-03-18 00:00:00': 'AW', '2018-04-16 00:00:00': 'FI', '2018-03-25 00:00:00': 'BY', '2018-03-29 00:00:00': 'CO', '2018-04-15 00:00:00': 'FE', '2018-04-03 00:00:00': 'DI', '2018-04-11 00:00:00': 'EO', '2018-04-09 00:00:00': 'EG', '2018-03-17 00:00:00': 'AS', '2018-03-22 00:00:00': 'BM', '2018-03-28 00:00:00': 'CK', '2018-03-26 00:00:00': 'CC', '2018-04-12 00:00:00': 'ES', '2018-03-16 00:00:00': 'AO', '2018-03-11 00:00:00': 'U', '2018-03-30 00:00:00': 'CS', '2018-03-19 00:00:00': 'BA', '2018-03-13 00:00:00': 'AC', '2018-04-04 00:00:00': 'DM', '2018-04-13 00:00:00': 'EW', '2018-04-14 00:00:00': 'FA', '2018-03-10 00:00:00': 'Q', '2018-03-15 00:00:00': 'AK', '2018-03-14 00:00:00': 'AG', '2018-04-08 00:00:00': 'EC', '2018-03-21 00:00:00': 'BI', '2018-04-05 00:00:00': 'DQ', '2018-03-24 00:00:00': 'BU', '2018-04-06 00:00:00': 'DU', '2018-03-27 00:00:00': 'CG', '2018-04-10 00:00:00': 'EK', '2018-04-02 00:00:00': 'DE', '2018-03-31 00:00:00': 'CW'}
e = {'91': 'CN', '90': 'CM', '93': 'CP', '167': 'FL', '95': 'CR', '94': 'CQ', '0': 'A', '97': 'CT', '8': 'I', '163': 'FH', '120': 'DQ', '121': 'DR', '122': 'DS', '123': 'DT', '124': 'DU', '125': 'DV', '126': 'DW', '127': 'DX', '128': 'DY', '129': 'DZ', '3': 'D', '108': 'DE', '109': 'DF', '102': 'CY', '103': 'CZ', '100': 'CW', '101': 'CX', '106': 'DC', '107': 'DD', '104': 'DA', '105': 'DB', '39': 'AN', '38': 'AM', '33': 'AH', '32': 'AG', '31': 'AF', '30': 'AE', '37': 'AL', '36': 'AK', '35': 'AJ', '34': 'AI', '60': 'BI', '61': 'BJ', '62': 'BK', '63': 'BL', '64': 'BM', '65': 'BN', '66': 'BO', '67': 'BP', '68': 'BQ', '69': 'BR', '6': 'G', '99': 'CV', '98': 'CU', '168': 'FM', '169': 'FN', '164': 'FI', '165': 'FJ', '166': 'FK', '92': 'CO', '160': 'FE', '161': 'FF', '162': 'FG', '96': 'CS', '41': 'AP', '1': 'B', '9': 'J', '146': 'EQ', '147': 'ER', '144': 'EO', '145': 'EP', '142': 'EM', '143': 'EN', '140': 'EK', '141': 'EL', '148': 'ES', '149': 'ET', '47': 'AV', '133': 'ED', '132': 'EC', '131': 'EB', '130': 'EA', '137': 'EH', '136': 'EG', '135': 'EF', '134': 'EE', '139': 'EJ', '138': 'EI', '24': 'Y', '25': 'Z', '26': 'AA', '27': 'AB', '20': 'U', '21': 'V', '22': 'W', '23': 'X', '28': 'AC', '29': 'AD', '4': 'E', '59': 'BH', '58': 'BG', '55': 'BD', '54': 'BC', '57': 'BF', '56': 'BE', '51': 'AZ', '50': 'AY', '53': 'BB', '52': 'BA', '115': 'DL', '114': 'DK', '88': 'CK', '116': 'DM', '111': 'DH', '110': 'DG', '113': 'DJ', '112': 'DI', '82': 'CE', '83': 'CF', '80': 'CC', '81': 'CD', '86': 'CI', '87': 'CJ', '84': 'CG', '85': 'CH', '7': 'H', '181': 'FZ', '179': 'FX', '178': 'FW', '177': 'FV', '176': 'FU', '175': 'FT', '174': 'FS', '173': 'FR', '172': 'FQ', '171': 'FP', '170': 'FO', '180': 'FY', '2': 'C', '11': 'L', '10': 'K', '13': 'N', '12': 'M', '15': 'P', '14': 'O', '17': 'R', '16': 'Q', '19': 'T', '18': 'S', '117': 'DN', '89': 'CL', '151': 'EV', '150': 'EU', '153': 'EX', '152': 'EW', '155': 'EZ', '154': 'EY', '157': 'FB', '156': 'FA', '159': 'FD', '158': 'FC', '48': 'AW', '49': 'AX', '46': 'AU', '119': 'DP', '44': 'AS', '45': 'AT', '42': 'AQ', '43': 'AR', '40': 'AO', '118': 'DO', '5': 'F', '77': 'BZ', '76': 'BY', '75': 'BX', '74': 'BW', '73': 'BV', '72': 'BU', '71': 'BT', '70': 'BS', '79': 'CB', '78': 'CA'}
datesss = {'2018-03-14 00:00:00': '8', '2018-03-08 00:00:00': '2', '2018-03-11 00:00:00': '5', '2018-03-18 00:00:00': '12', '2018-03-12 00:00:00': '6', '2018-03-24 00:00:00': '18', '2018-03-25 00:00:00': '19', '2018-03-10 00:00:00': '4', '2018-03-16 00:00:00': '10', '2018-03-06 00:00:00': '0', '2018-03-23 00:00:00': '17', '2018-03-20 00:00:00': '14', '2018-03-09 00:00:00': '3', '2018-03-19 00:00:00': '13', '2018-03-21 00:00:00': '15', '2018-03-13 00:00:00': '7', '2018-03-17 00:00:00': '11', '2018-03-07 00:00:00': '1', '2018-03-22 00:00:00': '16', '2018-03-15 00:00:00': '9'}
rb = load_workbook('sample1.xlsx')
wb = rb.active
s = socket.socket()
s.bind(("192.168.55.64",8082))
s.listen(1)


while True:
    c, addr = s.accept()
    data = c.recv(1024)
    
    decoded_data = data.decode("utf-8")
    hehe = decoded_data.split(",")
    m = re.search(r'\d+', hehe[0])
    numeric = m.group()
    print numeric
    mmmmm = int(numeric)+2
    now = str(date.today()) + " 00:00:00"
    now = str(date.today()) + " 00:00:00"
    n = re.search(r'\d+', hehe[1])
    session = n.group()
    print session   

    
    for i in range(0,30):
        b = e[str(i)]+str(mmmmm)
        d = wb[b]
        if (d.value == 'p'):
            count += 1
        elif (d.value == 'a'):
            absent += 1
        else :
            not_available += 1
    
    rara = new_data[now]
    print rara
    value_of_rara = inverse_of_e[str(rara)]
    if(session == '1'):
        print "session 1"
        queen =  int(value_of_rara)
    elif(session == '2'):
        queen =  int(value_of_rara)+1
        print "session 2"
    elif(session == '3'):
        queen =  int(value_of_rara)+ 2
        print "session 3"
    elif(session == '4'):
        queen =  int(value_of_rara)+3
        print "session 4"
    else:
        queen = 0
        print "queen error"
    king = e[str(queen)]
    print king
    for i in range(1,50):
        b = king+str(i)
        d = wb[b]
        if(d.value == None):
            nope += 1
        elif(d.value == 'p' or d.value == 'pp'):
            now_count += 1 
    c.send("attendance updated"+","+str(count)+","+str(absent)+ "," + str(not_available)+","+str(now_count))
    count = 0
    absent = 0
    nope = 0
    now_count = 0
    not_available = 0
    wb[king+str(mmmmm)] = 'p'
    c.close()
    rb.save('sample1.xlsx')
