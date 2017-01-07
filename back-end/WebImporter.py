# encoding: utf-8
from api.results import result_service

print 'Importing results from the web...'
result_service.import_results('http://www.cccc.cat/rss-resultats')
print 'Successfully imported'
