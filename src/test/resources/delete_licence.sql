create table if not exists licences_for_deletion(licence_id INTEGER NOT NULL, request_id INTEGER NULL);

delete from licences_for_deletion;

insert into licences_for_deletion (licence_id) values (123156);
insert into licences_for_deletion (licence_id) values (123333);
insert into licences_for_deletion (licence_id) values (123444);
insert into licences_for_deletion (licence_id) values (123555);
insert into licences_for_deletion (licence_id) values (123625);
insert into licences_for_deletion (licence_id) values (123888);
insert into licences_for_deletion (licence_id) values (124366);
insert into licences_for_deletion (licence_id) values (127093);
insert into licences_for_deletion (licence_id) values (126077);
insert into licences_for_deletion (licence_id) values (126631);
insert into licences_for_deletion (licence_id) values (128003);
insert into licences_for_deletion (licence_id) values (123665);
insert into licences_for_deletion (licence_id) values (128001);


insert into licences_for_deletion (licence_id, request_id) select licence_id, fulfilment_request_id from fulfilment_request_licences where licence_id in (123156, 123333, 123444, 123555, 123625, 123888, 124366, 126077, 126631, 127093, 128003, 123665, 128001);

delete from event_log where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_current_assets where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_request_assets where fulfilment_request_id in (select request_id from licences_for_deletion);

delete from fulfilment_production where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_request_licences where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_requests where id in (select request_id from licences_for_deletion);

drop table licences_for_deletion;
