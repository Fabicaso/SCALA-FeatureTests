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
insert into licences_for_deletion (licence_id) values (123667);
insert into licences_for_deletion (licence_id) values (123669);
insert into licences_for_deletion (licence_id) values (127099);
insert into licences_for_deletion (licence_id) values (122333);
insert into licences_for_deletion (licence_id) values (128002);


insert into licences_for_deletion (licence_id, request_id) select licence_id, request_id from fulfilment_current_assets where licence_id in (select licence_id from licences_for_deletion);

delete from event_log where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_current_assets where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_production where licence_id in (select distinct licence_id from licences_for_deletion);

delete from fulfilment_requests where id in (select request_id from licences_for_deletion);

drop table licences_for_deletion;
